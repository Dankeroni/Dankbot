[![Build Status](https://travis-ci.org/Dankeroni/dankbot.svg?branch=master)](https://travis-ci.org/Dankeroni/dankbot)

Dankbot is an irc-bot which moderates channels on twitch.tv.

The default directory for the bot data(this includes config.properties, config.commands) is you current path, you can specify any other path as a command line argument.

### Installation

Clone the repository `git clone https://github.com/Dankeroni/dankbot.git`

Cd into the bot directoy `cd dankbot`

To compile on linux/mac run `./gradlew build`, on windows run `gradlew build`

To configure the bot copy/move config.example to config.properties and change it as needed.

Start the bot `java -jar build/libs/dankbot.jar path`, path is optional.
