# dankbot [![Build Status](https://travis-ci.org/dankeroni/dankbot.svg?branch=master)](https://travis-ci.org/Dankeroni/dankbot)

dankbot is an irc-bot which moderates channels on twitch.tv.

The default directory for the bot data(this includes config.properties, config.commands) is you current path, you can specify any other path as a command line argument.

### Installation

1. Clone the repository `git clone https://github.com/dankeroni/dankbot.git`
2. Cd into the bot directoy `cd dankbot`
3. To compile on linux/mac run `./gradlew build`, on windows run `gradlew build`
4. To configure the bot copy/move config.example to config.properties and change it as needed.
5. Start the bot `java -jar build/libs/dankbot.jar path`, path is optional.
