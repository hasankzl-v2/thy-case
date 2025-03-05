# Full Stack Development Case Study

This project demonstrates a full-stack application for managing locations, transportation, and route finding, built with a modern stack including **React.js**, **TypeScript**, **Material UI**, **Spring Boot**, **PostgreSQL**, and other supporting technologies.

## Tech Stack

### Frontend

- **React.js** <img src="https://upload.wikimedia.org/wikipedia/commons/a/a7/React-icon.svg" alt="React Icon" width="20" /> - JavaScript library for building user interfaces.
- **TypeScript** <img src="https://upload.wikimedia.org/wikipedia/commons/4/4c/Typescript_logo_2020.svg" alt="TypeScript Icon" width="20" /> - Superset of JavaScript that adds static types.
- **Material UI** <img src="https://material-ui.com/static/logo.png" alt="Material UI Icon" width="20" /> - Popular React component library for building modern UIs.
- **Axios** <img src="https://axios-http.com/assets/logo.svg" alt="Axios Icon" width="30" /> - For making HTTP requests from the frontend to the backend.

### Backend

- **Spring Boot** <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT8i4zPog-0j0JR_yZglxPhTPZXxN2iMTQ3Dw&s" alt="Spring Boot Icon" width="20" /> - Java framework for building applications.
- **Swagger** <img src="https://pbs.twimg.com/profile_images/1451297216187011072/xLd1JSZk_400x400.png" alt="Swagger Icon" width="20" /> - For API documentation.
- **PostgreSQL** <img src="https://static-00.iconduck.com/assets.00/postgresql-icon-1987x2048-v2fkmdaw.png" alt="PostgreSQL Icon" width="20" /> - Open-source relational database.
- **Lombok** <img src="https://user-images.githubusercontent.com/1204509/79262490-b2012a80-7e91-11ea-82fa-e791f8b4d177.jpg" alt="Lombok Icon" width="20" /> - Java library that helps reduce boilerplate code.
- **Spring Boot Test** <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT8i4zPog-0j0JR_yZglxPhTPZXxN2iMTQ3Dw&s" alt="Spring Boot Icon" width="20" />- For testing the backend application.

## Features

### Frontend Pages:

1. **Location**:

   - CRUD operations for managing locations.
   - Create, Read, Update, Delete location data.
   - Input validation and form submission.

2. **Transportation**:

   - CRUD operations for managing transportation data.
   - Create, Read, Update, Delete transportation data.
   - Input validation and form submission.

3. **Route**:
   - A feature to find valid routes based on origin location, destination location, and operation date.

## Setup and Installation

### Prerequisites

Ensure you have the following tools installed on your local machine:

- **Node.js** <img src="https://upload.wikimedia.org/wikipedia/commons/d/d9/Node.js_logo.svg" alt="Node.js Icon" width="20" /> (For frontend development)
- **Yarn** or **npm** <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcShkLKIXoV9cqkHD-60xblHWwHPtn3BDpWW4w&s" alt="npm Icon" width="20" /> (Package managers for frontend dependencies)
- **Java 17** <img src="https://neiljbrown.com/wp-content/uploads/2021/11/java17-image.webp" alt="Java Icon" width="40" /> (For backend development)
- **PostgreSQL** <img src="https://static-00.iconduck.com/assets.00/postgresql-icon-1987x2048-v2fkmdaw.png" alt="PostgreSQL Icon" width="20" /> (For database management)

### Backend Setup (Spring Boot)

1. Clone the repository: git clone <repo-adress>
2. go to folder : cd case-casebackend
3. install dependencies: mvn install
4. run application: mvn spring-boot:run

### Frontend Setup (React.js)

1. Clone the repository:git clone <repo-adress>
2. go to folder : cd case-frontend
3. install dependencies: npm install
4. run application: npm run dev
