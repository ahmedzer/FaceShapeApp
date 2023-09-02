
import joblib
import numpy as np

from os.path import dirname, join
def predict(files_dir, x_val):
    # Construct the full path to the model file in the assets folder
    model_path = join(dirname(__file__), 'path_to_random_forest_model.joblib')

    # Load the model from the PKL file
    loaded_model = joblib.load(model_path)
    new_data = np.array(x_val)
    new_data = new_data.reshape(1, -1)
    prediction = loaded_model.predict(new_data)

    # If you want the predicted category names instead of numeric indices
    CATEGORIES = ["Heart", "Oblong", "Oval", "Round", "Square"]
    predicted_category = CATEGORIES[prediction[0]]
    return predicted_category
