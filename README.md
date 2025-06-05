# PropertyInCyberjaya - Desktop Property Rental Management Application

A comprehensive Java-based desktop application for managing property rentals in Cyberjaya, Malaysia. This system facilitates property management for administrators, property owners, agents, and potential tenants through an intuitive GUI interface.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [System Architecture](#system-architecture)
- [User Roles](#user-roles)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Usage](#usage)
- [Project Structure](#project-structure)
- [Technologies Used](#technologies-used)
- [Data Storage](#data-storage)
- [Security Features](#security-features)

## 🏠 Overview

PropertyInCyberjaya is a desktop-based property rental management system developed as part of the TCP2201 project. The application serves as an online tool for property owners, property agents, and potential tenants to manage property rentals efficiently. The system includes comprehensive features for property listing, user management, filtering, sorting, and rental transactions.

## ✨ Features

### Core Functionality
- **Property Management**: Add, view, edit, and delete property listings
- **User Registration & Authentication**: Secure user registration with admin approval
- **Multi-Role Support**: Different interfaces for Admin, Property Owner, Agent, and Tenant
- **Property Search & Filter**: Advanced filtering by type, location, facilities, and rental rates
- **Rental Management**: Property rental transactions and status tracking
- **Report Generation**: Comprehensive reporting for different user roles

### Advanced Features
- **Property Sorting**: Sort by rental price (ascending/descending), rental rate per sqft
- **Property Status Management**: Active/Inactive property status control
- **User Profile Management**: Edit personal information and credentials
- **Data Validation**: Input validation and error handling
- **CSV Data Management**: Persistent data storage using CSV files

## 🏗️ System Architecture

The application follows the **Model-View-Controller (MVC)** design pattern:

- **Model**: `Property` class - Handles data representation and business logic
- **View**: `User` class and GUI components - Manages user interface
- **Controller**: Role-specific classes (`Admin`, `Owner`, `Agent`, `Tenant`) - Handle user interactions

### Design Patterns Used
- **MVC Pattern**: Separates data, presentation, and control logic
- **Iterator Pattern**: Used for CSV file operations and data traversal
- **Inheritance**: `User` extends `Property`, role classes extend `User`

## 👥 User Roles

### 1. Administrator
- Validate and approve user registrations
- Manage all property listings
- View comprehensive reports
- Delete properties from the system
- Reactivate inactive properties

### 2. Property Owner
- Add new property listings
- View and manage own properties
- Search and filter properties
- Edit profile information

### 3. Property Agent
- Add property listings on behalf of owners
- View detailed property reports
- Access filtering and sorting features
- Generate business reports

### 4. Potential Tenant
- Browse available properties
- Filter properties by various criteria
- Rent properties
- Manage personal profile

## 🔧 Prerequisites

- **Java Development Kit (JDK)**: Version 8 or higher
- **BlueJ IDE**: Recommended for development and execution
- **Apache Commons CSV Library**: Version 1.9.0 (included in project)

## 🚀 Installation & Setup

### Using BlueJ (Recommended)

1. **Download and Install BlueJ**
   - Visit [BlueJ official website](https://www.bluej.org/)
   - Download and install BlueJ

2. **Clone/Download the Project**
   ```bash
   git clone <repository-url>
   cd Desktop-based-Property-Rental-Management-Application
   ```

3. **Configure Apache Commons CSV Library**
   - Open BlueJ
   - Go to `BlueJ` → `Preferences`
   - Select `Libraries` → `Add File`
   - Add both JAR files:
     - `RentalSystem/commons-csv-1.9.0.jar`
     - `RentalSystem/commons-csv-1.9.0-sources.jar`
   - Click `OK`
   - Go to `Tools` → `Reset Java Virtual Machine`

4. **Open and Run the Project**
   - Open the `RentalSystem` folder in BlueJ
   - Compile all classes
   - Right-click on `Main` class and select `void main(String[] args)`

### Alternative Setup (Command Line)

1. **Compile the Project**
   ```bash
   cd RentalSystem
   javac -cp ".:commons-csv-1.9.0.jar" *.java
   ```

2. **Run the Application**
   ```bash
   java -cp ".:commons-csv-1.9.0.jar" Main
   ```

## 📖 Usage

### Getting Started

1. **Launch the Application**
   - Run `Main.java` to start the application
   - The main page will display role selection options

2. **First-Time Setup**
   - Register as a new user (requires admin approval)
   - Default admin credentials may be required for initial setup

3. **User Registration Process**
   - Fill in registration form with personal details
   - Wait for admin approval
   - Login with approved credentials

### Navigation Guide

#### For Administrators
- **User Management**: Approve/reject user registrations
- **Property Management**: View, delete, and manage all properties
- **Reports**: Access comprehensive system reports

#### For Property Owners
- **Add Properties**: List new properties with complete details
- **Manage Listings**: View and edit property information
- **Reports**: View property performance reports

#### For Tenants
- **Browse Properties**: Search and filter available properties
- **Rent Properties**: Submit rental requests
- **Profile Management**: Update personal information

#### For Agents
- **Property Listings**: Add properties on behalf of owners
- **Client Reports**: Generate detailed property reports
- **Market Analysis**: Access sorting and filtering tools

## 📁 Project Structure

```
Desktop-based-Property-Rental-Management-Application/
├── RentalSystem/
│   ├── Main.java                    # Application entry point
│   ├── MainPage.java               # Main GUI interface
│   ├── User.java                   # Base user class with CSV operations
│   ├── Property.java               # Property model and data operations
│   ├── Admin.java                  # Administrator functionality
│   ├── Owner.java                  # Property owner functionality
│   ├── Agent.java                  # Property agent functionality
│   ├── Tenant.java                 # Tenant functionality
│   ├── *.csv                       # Data storage files
│   ├── commons-csv-1.9.0.jar      # Apache Commons CSV library
│   ├── commons-csv-1.9.0-sources.jar
│   ├── package.bluej               # BlueJ project file
│   └── Wallpaper.jpg              # Application background image
├── Report.pdf                      # Comprehensive project documentation
├── Group 2.iml                    # IntelliJ project file
├── .gitignore                      # Git ignore rules
└── README.md                       # This file
```

### Data Files (CSV)
- `admin.csv` / `adminRegistration.csv` - Administrator data
- `owner.csv` / `ownerRegistration.csv` - Property owner data
- `agent.csv` / `agentRegistration.csv` - Property agent data
- `tenant.csv` / `tenantRegistration.csv` - Tenant data
- `activeProperty.csv` / `inactiveProperty.csv` - Property listings
- `cyberproperty2.csv` - Main property database
- `rejected*.csv` - Rejected user registrations

## 🛠️ Technologies Used

- **Programming Language**: Java
- **GUI Framework**: Java Swing
- **Data Storage**: CSV files with Apache Commons CSV
- **Development Environment**: BlueJ IDE
- **Design Patterns**: MVC, Iterator
- **External Libraries**: Apache Commons CSV 1.9.0

## 💾 Data Storage

The application uses CSV (Comma-Separated Values) files for data persistence:

- **User Data**: Stores user credentials, personal information, and approval status
- **Property Data**: Contains property details, pricing, and availability status
- **Temporary Files**: Used for data processing and filtering operations

### Data Security
- User registration requires admin approval
- Password validation and confirmation
- Input sanitization to prevent CSV injection
- Role-based access control

## 🔒 Security Features

- **Admin Approval System**: All new user registrations require administrator validation
- **Role-Based Access**: Different permission levels for each user type
- **Input Validation**: Comprehensive form validation and error handling
- **Data Integrity**: CSV format validation and error recovery
- **Authentication**: Username/password authentication for all users

## 📄 License

This project was developed as part of an academic assignment for TCP2201 course at Multimedia University (MMU).

## 🐛 Known Issues

- CSV files must not contain commas in data fields
- Application requires BlueJ-specific library configuration
- GUI is optimized for desktop use only

## 🔮 Future Enhancements

- Database integration (MySQL/PostgreSQL)
- Web-based interface
- Mobile application support
- Advanced reporting and analytics
- Email notification system
- Payment integration

---

For detailed user documentation and system diagrams, please refer to the `Report.pdf` file included in the project.
