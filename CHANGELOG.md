# Changelog
All notable changes to this project will be documented in this file. The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## [1.9.0-SNAPSHOT] - 2020-05-09
### Added
- A deleted flag to price entity
- Deleting a price entry (by setting its deleted flag to true)
- Listing all deleted prices

### Changed
- List all prices: all -> not deleted prices
- Restricted updating a price to only non-deleted prices

## [1.8.0-SNAPSHOT] - 2020-05-02
### Added
- Updating a price entry

## [1.7.0-SNAPSHOT] - 2020-05-02
### Fixed
- Throw InvalidArgumentException when adding a price if impossible to determine item or location. That is, when item ID or location ID are null, and item name or location name are not present either.

### Added
- InvalidArgumentException mapped to Bad Request (status 400)

## [1.6.0-SNAPSHOT] - 2020-05-02
### Added
- NotFoundException
- Controller advice

## [1.5.0-SNAPSHOT] - 2020-05-01
### Added
- Adding and viewing a price entry
- Entities for price and dictionaries

## [1.4.0-SNAPSHOT] - 2020-04-30
### Added
- API layout: add, update, delete, load one by id, list all, search, list price dynamics, list recently deleted, delete permanently.

## [1.3.0-SNAPSHOT] - 2020-04-26
### Added
- JOOQ config and exception translator

## [1.2.0-SNAPSHOT] - 2020-04-26
### Added
- Datasource and Flyway config

## [1.1.0-SNAPSHOT] - 2020-04-26
### Added
- Initial project setup
