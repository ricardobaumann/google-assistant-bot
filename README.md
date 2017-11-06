# Google assistant bot backend

Hello, I am the backend for the weltN24 newsticker bot. I deliver the newsticker content snippets through a google assistant Dialog Flow's interface, and also an audio file transcription
of the top 3 newstickers. 

<img src="https://medias2.prestastore.com/835054-pbig/chat-bot-for-social-networking.jpg" width="600px">

## Implementation

I was built as an AWS lambda, with 2 function handlers:
1. API gateway on `POST /question` to handle incoming requests from dialog flow. Through this endpoint, I can deliver the newstickers one by one of the full audio file transcription. The transcription, for now, is limited to the top 3 newstickers. 
2. The audio file generation scheduler, in charge of gathering the content snippets and forward it to the audio file generation lambda. 

## Deploy
Run `./deploy_lambda.sh` to package and deploy the lambda package. If the API gateway endpoint url changes, it must be updated on the dialog flow configuration. 

## Suggestions and enhancements
Join us on [#google-assistant (https://wod.slack.com/messages/C7E22JG2J)] for further discussions. 