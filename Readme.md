Worker was done using pure java

Description:
- class EchangeRatesProvider makes calls to bank.gov.ua to get the current rate
- class GaRequest is a model of request
- class EventsSender responsible for creating and sending events to GA(https://www.google-analytics.com/mp/collect), it uses measurment_id and secret obtained from GA, and client_id obtained from Google tag, which is located now in main.html, and generally should be used to send events to GA.
- class App is entry point which starts worker and in the infinite loop get usd exchange rate and send event with it to GA, with pause 10s between requests

To run worker you need to have java 17 installed, run: java -jar hw4.jar
