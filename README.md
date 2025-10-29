# Recruiting Website (Final Year Project)

This project is a **Recruitment Management System** developed as a **Final Year Engineering Project**.  
It helps recruiters post jobs, view applicants, and schedule interviews, while job seekers can search and apply for jobs easily.

---

## 🧠 Project Overview

The **Recruiting Website** is designed to simplify the hiring process by automatically sorting and ranking resumes based on the job description.  
It includes separate dashboards for **Recruiters** and **Applicants**, and provides features such as filtering, sorting, and viewing candidate details.

---

## 🚀 Features

### 👨‍💼 Recruiter
- Post and manage job openings.  
- View all posted jobs in one place.  
- See the list of applicants for each job.  
- Filter and sort candidates by experience and skills.  
- View detailed candidate profiles.  
- Schedule interviews and view all scheduled meetings.  

### 👨‍🎓 Applicant
- Create and manage profile.  
- Search for jobs using keywords, location, and type.  
- View recommended jobs dynamically.  
- Apply for jobs directly from the dashboard.  

### 🧰 Admin & Shared Features
- Authentication (Signup/Login).  
- Secure backend using Spring Boot and MongoDB.  
- Interactive and responsive frontend.  
- Gradient background theme for consistency:  
  `background: linear-gradient(to right, #8360c3, #2ebf91);`  

---

## 🏗️ Tech Stack

| Layer | Technology Used |
|-------|------------------|
| **Frontend** | HTML, CSS, JavaScript |
| **Backend** | Java Spring Boot |
| **Database** | MongoDB |
| **Tools** | Spring Tool Suite / IntelliJ IDEA, MongoDB Compass, GitHub |

---

## ⚙️ Project Setup and Installation

Follow these steps to run the project on your local system.

### 🖥️ Backend Setup (Spring Boot)

1. Install **Java JDK (17 or later)** and **Maven** on your system.  
2. Open the project folder in **Spring Tool Suite** or **IntelliJ IDEA**.  
3. Configure the `application.properties` file as follows:

   ```properties
   spring.data.mongodb.uri=mongodb://localhost:27017/recruitingdb
   server.port=8080
   ```

4. Start the **MongoDB server** on your system.  
   - If installed locally, run:
     ```bash
     mongod
     ```
5. Run the Spring Boot application:
   ```bash
   mvn spring-boot:run
   ```
6. The backend will start at [http://localhost:8080](http://localhost:8080)

---

### 🌐 Frontend Setup

1. Open the frontend folder in your preferred code editor (VS Code recommended).  
2. Ensure your HTML, CSS, and JavaScript files are properly linked.  
3. Open the main `index.html` file in your browser to run the frontend.  
4. The frontend will communicate with the backend API running on port `8080`.

---

## 🧩 Project Modules

### 🔹 Recruiter Module
- Post Job  
- View Posted Jobs  
- View Applied Candidates  
- Filter/Sort Candidates  
- View Detailed Candidate Profile  
- View Scheduled Meetings  

### 🔹 Applicant Module
- Search Jobs  
- Recommended Jobs  
- Apply for Jobs  
- View Application Status  

---

## 🧱 Folder Structure

```
Recruiting-Website/
│
├── backend/
│   ├── src/main/java/com/project/recruiting/
│   ├── src/main/resources/
│   └── pom.xml
│
├── frontend/
│   ├── index.html
│   ├── recruiter/
│   ├── applicant/
│   ├── css/
│   ├── js/
│   └── images/
│
└── README.md
```

---

## 📅 Team Members

- **Atharva Salunke**  
- **Mitali Bhole**  
- **Rajkumar Chavan**  
- **Bhavik Patil**

All are final-year Computer Engineering students at **Alard College of Engineering, Pune**.

---

## 🧾 License

This project is developed for academic purposes and is not intended for commercial use.

---

## 💬 Acknowledgment

We would like to thank our college faculty and mentors for their guidance and support during the development of this project.
