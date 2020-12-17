# ConcourseCI Support for IntelliJ

![Build](https://github.com/cedricziel/idea-concourse/workflows/Build/badge.svg)
[![Version](https://img.shields.io/jetbrains/plugin/v/15565-concourse-ci-support.svg)](https://plugins.jetbrains.com/plugin/15565-concourse-ci-support)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/15565-concourse-ci-support.svg)](https://plugins.jetbrains.com/plugin/15565-concourse-ci-support)

## What does it do

<!-- Plugin description -->
This plugin integrates knowledge about ConcourseCI pipeline files in IntelliJ based IDEs.

Structure:
* the plugin provides a JSON schema to the IDE to provide stable autocompletion
  and validation for keys in both pipelines and task files

Validation:
* the plugin provides some inspections to avoid referencing invalid data
<!-- Plugin description end -->

## Installation

- Using IDE built-in plugin system:
  
  <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "idea-concourse"</kbd> >
  <kbd>Install Plugin</kbd>
  
- Manually:

  Download the [latest release](https://github.com/cedricziel/idea-concourse/releases/latest) and install it manually using
  <kbd>Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>


---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template

## License

MIT
