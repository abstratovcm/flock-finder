<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Farm Management System</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <h1 class="title">Farm Management System</h1>
    <div class="homepage-options">
        <div class="option">
            <h2>Login</h2>
            <form action="login" method="post">
                <label for="username">Username:</label>
                <input type="text" id="username" name="username" required>
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
                <input type="submit" value="Login">
            </form>
        </div>
        <div class="option" id="dashboard-area">
             <h2 class="top">Access Dashboard</h2>
              <a href="dashboard.jsp" class="dashboard-link">Go to Cattle Weight Dashboard</a>

        </div>
    </div>
</body>
</html>