# Changelog

## [Unreleased]
### Added
* Added support for IntelliJ 2022.1
### Changed

### Deprecated

### Removed

### Fixed

### Security

## [0.0.12]
### Added
- Support platform version 213

### Changed
- Migrate getResource usage to reference current classloader
- Migrate getIcon usage to reference current classloader
- Override getDisplayName for custom file types

### Deprecated

### Removed

### Fixed

### Security

## [0.0.11]
### Added
- Add support for IDEA platform 212

### Changed
- Upgrade `io.gitlab.arturbosch.detekt` to 1.18.1
- Upgrade `org.jlleitschuh.gradle.ktlint` to 10.2.0
- Fix Ktlint issue

## [0.0.10]
### Added
- Add support for IDEA platform 211

### Changed
- Update gradle-intellij plugin to 0.7.2
- Use 2020.3.3 for plugin verifier

### Removed
- Remove support for 203 platform

## [0.0.9]
### Added
- Add test case for json schema validation 
- Modify do-step schema to be type array

## [0.0.8]
### Added
- Add support for top level keys `groups` and `display_config

## [0.0.7]
### Added
- Create distinct file types to detect pipeline and task files
- Add new file actions and templates

## [0.0.6]
### Added
- add `in_parallel` schema

### Changed
- Do not verify on 201 anymore
- Build with 202 to avoid compatibility issues

## [0.0.5]
### Added
- new baseline for compatibility is 2020.2
- migrate line marker providers to new parent signature

## [0.0.4]
### Added
- add custom plugin icon

## [0.0.3]
### Added
- Recognize concourse pipeline files by top level yaml keys
- Remove "IntelliJ" from plugin name

## [0.0.2]
### Added
- add references to Inputs and Outputs to enable completion
  and jump-to-definition
- add Concourse JSON Schema Provider for schema validation

## [0.0.1]
### Added
- Initial scaffold created from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)
- Inspections for missing resources and missing resource types
- autocompletion for resources and resource types
- Input and Output indexing
