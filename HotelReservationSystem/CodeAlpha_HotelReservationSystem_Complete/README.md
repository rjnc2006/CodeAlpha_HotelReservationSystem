# Hotel Reservation System

A Java console-based Hotel Reservation System that allows users to search, book, view, and cancel hotel room reservations.

## Project Description

The Hotel Reservation System simulates a simple hotel booking environment. Users can view available rooms, search rooms by category, make reservations, view booking details, calculate the total booking amount, and cancel reservations.

The project uses Object-Oriented Programming (OOP), `ArrayList`, and Java File I/O to store and restore reservation data.

## Features

- View available rooms
- Room categories: Standard, Deluxe, and Suite
- Search available rooms by category
- Book a room
- Generate a unique reservation ID
- View reservation details
- Calculate total booking amount
- Simulated payment/booking confirmation
- Cancel a reservation
- Automatically make cancelled rooms available again
- Save reservations to a file
- Load saved reservations when the program starts
- Input validation
- Menu-driven console interface

## Technologies Used

- Java
- Object-Oriented Programming (OOP)
- ArrayList
- Scanner
- File I/O (`FileReader`, `FileWriter`, `BufferedReader`, `BufferedWriter`)

## Project Structure

```text
CodeAlpha_HotelReservationSystem
├── README.md
├── .gitignore
├── data
│   └── reservations.txt
└── src
    ├── Room.java
    ├── Reservation.java
    ├── FileManager.java
    └── HotelReservationSystem.java
```

## How to Run

Open a terminal in the project folder.

### 1. Enter the source folder

```bash
cd src
```

### 2. Compile

```bash
javac Room.java Reservation.java FileManager.java HotelReservationSystem.java
```

### 3. Run

Run the application from the **project root folder** so that the `data/reservations.txt` path works correctly.

If your terminal is currently inside `src`, first go back:

```bash
cd ..
```

Then run:

```bash
java -cp src HotelReservationSystem
```

## Menu Options

1. View Available Rooms
2. Book a Room
3. View Reservations
4. Cancel Reservation
5. Search Rooms by Category
6. Exit

## Sample Rooms

| Room | Category | Price per Night |
|---|---|---:|
| 101 | Standard | 2000 |
| 102 | Standard | 2000 |
| 201 | Deluxe | 3500 |
| 202 | Deluxe | 3500 |
| 301 | Suite | 5500 |
| 302 | Suite | 5500 |

## Booking Flow

```text
View Available Rooms
        ↓
Select Room
        ↓
Enter Guest Name
        ↓
Enter Number of Nights
        ↓
Create Reservation
        ↓
Calculate Total Amount
        ↓
Save Reservation to File
        ↓
Room Becomes Unavailable
```

## Cancellation Flow

```text
View Reservations
        ↓
Enter Reservation ID
        ↓
Cancel Reservation
        ↓
Update File
        ↓
Room Becomes Available Again
```

## File Storage

Reservation information is stored in:

```text
data/reservations.txt
```

The file stores:

```text
Reservation ID | Guest Name | Room Number | Nights
```

The application loads this file when it starts and saves it whenever a reservation is created or cancelled.

## Payment Simulation

The system calculates the total amount using:

```text
Total Amount = Price Per Night × Number of Nights
```

The project does not process real payments. It only simulates the booking/payment calculation for educational purposes.

## Learning Objectives

This project demonstrates:

- Classes and objects
- Constructors
- Encapsulation
- ArrayList
- Methods
- Loops
- Conditional statements
- User input using Scanner
- Object relationships
- File I/O
- Persistent data storage
- Room availability management
- Reservation management

## Note

This is an educational hotel reservation simulation. It does not connect to a real hotel, payment gateway, or external booking service.