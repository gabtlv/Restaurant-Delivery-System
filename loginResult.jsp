<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Result</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding-top: 100px; text-align: center; margin: 0; }
        .error-box { display: inline-block; background-color: white; padding: 40px; width: 100%; max-width: 350px; border-radius: 15px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); }
        h2 { color: #c0392b; margin-bottom: 15px; }
        p { color: #7f8c8d; margin-bottom: 25px; }
        .back-link { display: block; padding: 12px; background-color: #3498db; color: white; text-decoration: none; border-radius: 25px; font-weight: bold; }
        .back-link:hover { background-color: #2980b9; }
    </style>
</head>
<body>

    <div class="error-box">
        <h2>Login Failed</h2>
        <p>Invalid email or password. Please try again.</p>
        <a href="login.html" class="back-link">Try Again</a>
    </div>

</body>
</html>