# TrainNotifier
Android app that provides automatic train updates for regular journeys (e.g. daily train journeys to and from work).

Two gifs of the app:
[Gif #1](http://i.imgur.com/ZZfNaTE.gifv), [Gif #2](http://i.imgur.com/8BvsfW9.gifv)

---

## What does it do?
The app will automatically pop up with a notification before (e.g. 30 minutes) your scheduled train journey to provide updates on whether
the train is on time, delayed, or cancelled. Every time there is a change to the journey (the train's expected arrival time has changed,
train has been cancelled, train has changed from late to on time, etc) a new notification will appear with the updated information
and the phone will vibrate to alert you.

##  Why?
I have regularly scheduled train journeys (train to and from college) and it's helpful to know the status of my train constantly both
before I leave college and during the walk to the station to know whether or not I need to go to a different station or take a different
train by simply looking at my lock screen to get a real time status about my train.

---

## Searches

The search screen allows you to enter the stations for your journey and search for the various journeys. Searches are saved and
displayed on a recent searches list that can be used to input the stations.

<img src="http://i.imgur.com/jHAlIpG.png" width="360"> <img src="http://i.imgur.com/A7fATSC.png" width="360">

## Saved Journeys

After searching for a journey and saving a journey to poll it for updates, it appears on your saved journey list and can be removed.

<img src="http://i.imgur.com/sNMwkru.png" width="360"> <img src="http://i.imgur.com/1tsSruL.png" width="360">

## Polling

After having saved a journey to poll, a set amount of time (e.g. 30 min) before the train is supposed to arrive at the station, polling
will occur and a notification will appear to serve the status of the train. Whenever there is a change to the status of the train, the phone
will vibrate to alert you of the change. When clicking on the notification, an activity opens up detailing the journey - real time data
of the train is provided if it can be retrieved (to tell which station the train is currently at or which station is it travelling to) as
well as other data (platform, expected arrival time, delay information, cancellation information, etc).

<img src="http://i.imgur.com/dgZZOiu.jpg" width="360"> <img src="http://i.imgur.com/U9zk8hz.png" width="360">
