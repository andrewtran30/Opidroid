# Opidroid: A fully autuonomous, Sub-Q Drug Delivery Platform for Postoperative Pain Remediation
The Opidroid was designed to deliver personalized morphine doses for postoperative patients through an intelligent use of machine learning. The device leverage a 3 stage process in order to complete its task. The following code represents the machine learning apsect of the project. 

### (1) Machine Learning
Data acquisition and exploratory analysis were performed on 2 data sets and trained on two models. The first model applies an EEG dataset towards a Random Forest Tree to classify 3 emotions: HAPPY, SAD, or NEUTRAL. The second model applies a multimodal physiological dataset towards a traditional Feedforward Neural Network to classify physiological conditions: STRESSED, AMUSED, or BASELINE. A combination of SAD and STRESSED indicates a patient is in pain, signaling to the mobile app the patient's state. Contact me for datasets. 
### (2) Mobile App Integration
The mobile application integrates the machine learning models via TFLite and Google Cloud Services. The mobile app processes the pain signal and uploads it to a database containing the user's health profile: weight, age, blood pressure, and other relevant metrics. The app then forwards the pain signal, along with a variety of other factors to the hardware.
### (3) Hardware Integration
A bluetooth chip nested within an Arduino module recieves the digital messages from the mobile application and forwards a personalized morphine dose throughout the circuit. The personalization mechanism is coded in the Arduino IDE; a 3v motor attached to a drug delivery pump controls the amount of morphine delivered by the pump. The more pain a patient experiences according to his/her health metrics combined with the given pain classification, the more the motor spins, thus pumping out more morphine. 
###
Relevant links: https://www.vabio.org/falls-church-teen-competes-for-virginia/, https://www.bio.org/iambio
