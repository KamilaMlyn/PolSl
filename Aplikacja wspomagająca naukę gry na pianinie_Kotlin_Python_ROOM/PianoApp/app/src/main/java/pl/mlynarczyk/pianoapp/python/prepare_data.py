import os  # Do operacji na ścieżkach plików
import numpy as np  # Do obsługi danych numerycznych
import librosa  # Do pracy z plikami audio i analizą dźwięku
#import pretty_midi  # Do pracy z plikami MIDI
import tensorflow as tf  # Do używania TensorFlow Lite

def python_test(i):
    return i+1

SAMPLE_RATE = 22050
N_MFCC = 13
N_FFT = 2048
HOP_LENGTH = 512
target_length = 9560

def make_spectogram(path):
    #full_path = os.path.join(path, filename)
    if(os.path.splitext(path)[1] == '.mid'):
        midi_data = pretty_midi.PrettyMIDI(full_path)
        signal = midi_data.synthesize()
        sr = SAMPLE_RATE
    else:
        signal, sr = librosa.load(path, sr = SAMPLE_RATE) #signal - tablica wartości (sr * T = 22050 * czas_trwania_utworu)
        #spektogram
    mfcc = librosa.feature.mfcc(y = signal,
                                sr = sr,
                                n_mfcc = N_MFCC,
                                n_fft = N_FFT,
                                hop_length = HOP_LENGTH) #
    mfcc = mfcc.T
    mfcc = mfcc.tolist()

    repeated_mfcc = []
    padded_data_mfcc = []
    while len(repeated_mfcc) < target_length:
            repeated_mfcc.extend(mfcc)  # Extend the values cyclically until `target_length`
        # Slice the extended MFCC data to ensure it doesn't go beyond `target_length`
    repeated_mfcc = repeated_mfcc[:target_length]
    padded_data_mfcc.append(repeated_mfcc)

    padded_data_mfcc = np.array(padded_data_mfcc)[..., np.newaxis]
    return padded_data_mfcc

def make_prediction_emotions(mfcc,modelPath):

    interpreter = tf.lite.Interpreter(model_path=modelPath)
    interpreter.allocate_tensors()

    # Pobranie informacji o wejściu/wyjściu
    input_details = interpreter.get_input_details()
    output_details = interpreter.get_output_details()

    # Przygotowanie danych wejściowych
    input_shape = input_details[0]['shape']
    #input_data = np.array(np.random.random(input_shape), dtype=np.float32)  # Przykladowe dane wejściowe
    mfcc = np.array(mfcc, dtype=np.float32)  # Konwersja na np.float32
    # Wprowadzenie danych do modelu
    interpreter.set_tensor(input_details[0]['index'], mfcc)

    # Uruchomienie modelu
    interpreter.invoke()

    # Pobranie wyników
    output_data = interpreter.get_tensor(output_details[0]['index'])
    print("Wynik przewidywania:", output_data)
    return output_data

def get_emotions(path,modelPath):
    mfcc = make_spectogram(path)
    #return mfcc.tolist()
    result = make_prediction_emotions(mfcc,modelPath)
    #print(type(result)) #numpy.ndarray

    return result #.tolist()