# Smart Contact Manager

**Smart Contact Manager** is a comprehensive application designed to efficiently manage and view contact information. Developed using Spring Boot, SQL, Tailwind CSS, and HTML, this project incorporates modern features such as user authentication, pagination, and cloud integration.

## Features

- **User Signup:** 
  - Sign up using email, Google, or GitHub.
  - Email verification for added security.

- **Contact Management:**
  - Add, update, and delete contacts.
  - Search functionality to quickly find contacts.

- **Pagination:**
  - Efficiently view contacts with pagination to handle large datasets.

- **Cloud Integration:**
  - Store contact pictures using Cloudinary for reliable cloud storage and management.

- **Email Functionality:**
  - Compose and send emails with attachments.
  - Export contact data to Excel for better user management.

## Tech Stack

- **Spring Boot:** Backend framework for developing the application.
- **SQL:** Database management for storing user and contact information.
- **Tailwind CSS:** Utility-first CSS framework for styling the frontend.
- **HTML:** Markup language for structuring the content.
- **JPA:** Java Persistence API for managing database interactions.
- **Cloudinary:** Cloud storage service for managing and storing contact pictures.
  ## Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/TanishaMehta17/Smart_Contacts_Manager/


2. **Navigate to the project directory:**
   ```bash
   inside Java Projects
   run the scm directory 

## Configuration

### Backend Setup

#### Spring Boot Configuration

- **Configure application properties** in `src/main/resources/application.properties`.
- **Set up database connections** and Cloudinary settings as required.

#### Cloudinary Integration

- **Sign up for a Cloudinary account** and obtain your API credentials.
- **Configure Cloudinary settings** in the application properties:

  ```properties
  cloudinary.url=https://api.cloudinary.com/v1_1/your-cloud-name
  cloudinary.apiKey=your-api-key
  cloudinary.apiSecret=your-api-secret
### Email Functionality

- **Integrate email service** for sending emails with attachments.
- **Configure email server settings** in the application properties.

### Frontend Setup

#### Tailwind CSS Integration

- **Ensure Tailwind CSS is properly configured** in your project for styling.
- **Update `tailwind.config.js`** and `postcss.config.js` as needed.

## Contributing

We welcome contributions to Smart Contact Manager! To contribute:

1. **Fork the repository.**
2. **Create a new branch.**
3. **Make your changes.**
4. **Commit your changes.**
5. **Push to the branch.**
6. **Open a pull request.**

## Contact

For questions or support, please contact [tanishamehta1709@gamil.com](mailto:tanishamehta1709@gmail.com).

Feel free to make any additional modifications based on your specific needs!
