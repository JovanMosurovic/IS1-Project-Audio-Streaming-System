# Audio Streaming System

This project showcases a distributed **Audio Streaming System** developed in Java, focused on applying modern approaches to service-oriented architecture and inter-process communication. By implementing RESTful APIs with **JAX-RS**, asynchronous messaging with **JMS**, and data persistence using **MySQL**, I gained hands-on experience with some of the most popular and widely-adopted technologies for building scalable backend systems. The project covers the complete flow of REST API communication, backend message routing, modular service design, and relational database integration.

The client application itself is designed much like a specialized **API client tool** — similar in spirit to [Postman](https://www.postman.com/) — providing a graphical interface for constructing, sending, and analyzing HTTP requests and responses throughout the distributed system.

> The primary motivation behind this project was to learn **RESTful API communication** using **JAX-RS**, as well as asynchronous messaging with **JMS** in Java. Through implementing a multi-tier, service-based system, I deepened my understanding of distributed architectures, modern Java frameworks, and real-world API design patterns.
>
> This project was developed as the [university assignment](instructions.pdf) for the "Information Systems 1" course at the University of Belgrade School of Electrical Engineering, majoring in Software Engineering. Please refer to the file for detailed assignment instructions.

## Table of Contents

- [Project demo](#project-demo)
  - [Example Logs from Subsystems](#example-logs-from-subsystems)
- [System Overview](#system-overview)
  - [Client Application](#client-application)
  - [Central Server](#central-server)
  - [Subsystems](#subsystems)
- [Data Model Mapping](#data-model-mapping-serbian-to-english)
- [Features & Supported Operations](#features--supported-operations)
- [Diagrams](#diagrams)
- [Assignment Reference](#assignment-reference)


## Project demo

https://github.com/user-attachments/assets/e1ec886d-d56a-418f-9da5-fb34a6f83210

> **Note:** The demo showcases only some of the features of the project. <br />

### Example Logs from Subsystems

| Subsystem 1 | Subsystem 2 | Subsystem 3 |
|-----------------------------|----------------------------|----------------------------|
| ![Subsystem1_log](https://github.com/user-attachments/assets/14be54ad-9f0d-4662-a357-f3b3536b210f) | ![Subsystem2_log](https://github.com/user-attachments/assets/432e362c-3b5c-41f7-a4c4-44cdcfee327f) | ![Subsystem3_log](https://github.com/user-attachments/assets/b405fa3c-d532-4018-b3ae-9eb9fb2346a7) |

> **Note:** These logs include requests shown in the demo as well as **additional example operations**.
>
> They were executed **after recording the demo**, so some **IDs or request parameters may differ** from the original video.

## System Overview

### Client Application

- GUI inspired by **[Postman](https://www.postman.com/)**
- Developed using **JavaFX**
- Allows users to issue requests
- Sends **REST requests** to the central server
- Displays server responses via the interface

### Central Server

- Implements a **REST API** for all 27 supported operations
- Forwards requests to one of three subsystems via **JMS**
- **Stateless** – does not store any data locally
- Routes requests based on functionality

### Subsystems

Each subsystem uses a **MySQL** database and communicates **exclusively via JMS**.

- **Subsystem 1**: Cities and Users
- **Subsystem 2**: Audios and Categories
- **Subsystem 3**: Packages, Subscriptions, Ratings, Listenings, Favorites

## Data Model Mapping (Serbian to English)

Below is a complete mapping of the database schema from Serbian (original table and column names) to English, with descriptions for each field. This helps non-Serbian speakers understand the data structure and purpose of each entity in the system.

### `mesto` (city)

Stores information about cities.

| Column (Serbian) | Column (English) | Type          | Description                |
|------------------|------------------|---------------|----------------------------|
| `mesto_id`       | `city_id`        | INT, PK       | Unique identifier.         |
| `naziv`          | `name`           | VARCHAR(100)  | Name of the city.          |

### `korisnik` (user)

| Column (Serbian) | Column (English) | Type                 | Description                                  |
|------------------|------------------|----------------------|----------------------------------------------|
| `korisnik_id`    | `user_id`        | INT, PK              | Unique identifier.                           |
| `ime`            | `name`           | VARCHAR(100)         | Name of the user.                            |
| `email`          | `email`          | VARCHAR(100), UNIQUE | User email address.                          |
| `godiste`        | `birth_year`     | INT                  | Birth year of the user.                      |
| `pol`            | `gender`         | ENUM('MUSKI', 'ZENSKI', 'DRUGO') | Gender.       |
| `mesto_id`       | `city_id`        | INT, FK              | Reference to the city the user is from.      |

### `audio_snimak` (audio recording)

| Column (Serbian)         | Column (English)     | Type          | Description                                |
|--------------------------|----------------------|---------------|--------------------------------------------|
| `audio_id`               | `audio_id`           | INT, PK       | Unique identifier.                         |
| `naziv`                  | `name`               | VARCHAR(200)  | Name of the audio.                         |
| `trajanje`               | `duration`           | INT           | Duration in seconds.                       |
| `vlasnik_id`             | `owner_id`           | INT, FK       | Reference to the user who owns the audio.  |
| `datum_vreme_postavljanja` | `upload_timestamp` | DATETIME      | Upload date and time.                      |

### `kategorija` (category)

| Column (Serbian) | Column (English) | Type           | Description                   |
|------------------|------------------|----------------|-------------------------------|
| `kategorija_id`  | `category_id`    | INT, PK        | Unique identifier.            |
| `naziv`          | `name`           | VARCHAR(100), UNIQUE | Category name.         |

### `audio_kategorija` (audio-category)

| Column (Serbian)      | Column (English)      | Type      | Description                                 |
|------------------------|------------------------|-----------|---------------------------------------------|
| `audio_kategorija_id`  | `audio_category_id`    | INT, PK   | Unique identifier.                          |
| `audio_id`             | `audio_id`             | INT, FK   | Linked audio.                               |
| `kategorija_id`        | `category_id`          | INT, FK   | Linked category.                            |

### `paket` (package)

| Column (Serbian)     | Column (English)      | Type             | Description                       |
|----------------------|------------------------|------------------|-----------------------------------|
| `paket_id`           | `package_id`           | INT, PK          | Unique identifier.                |
| `trenutna_cena`      | `current_price`        | DECIMAL(10,2)    | Current monthly price.            |

### `pretplata` (subscription)

| Column (Serbian)          | Column (English)       | Type             | Description                                     |
|---------------------------|------------------------|------------------|-------------------------------------------------|
| `pretplata_id`            | `subscription_id`      | INT, PK          | Unique identifier.                              |
| `korisnik_id`             | `user_id`              | INT, FK, UNIQUE  | Subscribed user (one active subscription only). |
| `paket_id`                | `package_id`           | INT, FK          | Subscribed package.                             |
| `datum_vreme_pocetka`     | `start_time`           | DATETIME         | Subscription start time.                        |
| `placena_cena`            | `paid_price`           | DECIMAL(10,2)    | Price at the time of subscription.              |

### `istorija_slusanja` (listening history)

| Column (Serbian)          | Column (English)         | Type      | Description                          |
|---------------------------|--------------------------|-----------|--------------------------------------|
| `istorija_slusanja_id`    | `listening_id`           | INT, PK   | Unique identifier.                   |
| `korisnik_id`             | `user_id`                | INT, FK   | User who listened.                   |
| `audio_id`                | `audio_id`               | INT, FK   | Audio being listened to.             |
| `datum_vreme_pocetka`     | `start_time`             | DATETIME  | Listening start timestamp.           |
| `pocetni_sekund`          | `start_second`           | INT       | Second at which the user started.    |
| `broj_odslusanih_sekundi` | `seconds_listened`       | INT       | Number of seconds listened.          |

### `ocena` (rating)

| Column (Serbian)      | Column (English)    | Type      | Description                               |
|------------------------|---------------------|-----------|-------------------------------------------|
| `ocena_id`             | `rating_id`         | INT, PK   | Unique identifier.                        |
| `korisnik_id`          | `user_id`           | INT, FK   | User giving the rating.                   |
| `audio_id`             | `audio_id`          | INT, FK   | Audio being rated.                        |
| `vrednost`             | `score`             | INT       | Rating value (1–5).                       |
| `datum_vreme_ocene`    | `rating_timestamp`  | DATETIME  | Date and time when the rating was given.  |

### `omiljeni_audio` (favorite audio)

| Column (Serbian)     | Column (English)    | Type     | Description                            |
|----------------------|---------------------|----------|----------------------------------------|
| `omiljeni_audio_id`  | `favorite_id`       | INT, PK  | Unique identifier.                     |
| `korisnik_id`        | `user_id`           | INT, FK  | User who favorited the audio.          |
| `audio_id`           | `audio_id`          | INT, FK  | Favorited audio.                       |


## Features & Supported Operations

All following features are accessible via REST endpoints on the central server. Each request is routed to the appropriate subsystem via JMS:

### Subsystem 1

- Create city
- Create user
- Change user email
- Change user city
- Get all cities
- Get all users

### Subsystem 2

- Create category
- Create audio
- Update audio title
- Add category to audio
- Delete audio (only by its owner)
- Get all categories
- Get all audios
- Get categories for specific audio

### Subsystem 3

- Create package
- Change package price
- Subscribe user to a package
- Listen to audio
- Add audio to favorites
- Rate audio (create/update/delete)
- Get all subscriptions for user
- Get all listenings for audio
- Get all ratings for audio
- Get user’s favorite audios

## Diagrams

UML diagrams are available in the `uml` directory.

## Assignment Reference

Course: **Information Systems 1 ([13S113IS1](https://www.etf.bg.ac.rs/en/fis/karton_predmeta/13S113IS1-2013))**  
Academic Year: **2024/2025**  
University of Belgrade, School of Electrical Engineering  
