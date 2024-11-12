// Get the popup button and popup message elements
const popupButton = document.getElementById('popupButton');
const popupMessage = document.getElementById('popupMessage');

// Add event listener for the popup button click
popupButton.addEventListener('click', () => {
  popupMessage.style.display = 'block'; // Show the popup
});

// Function to close the popup
function closePopup() {
  popupMessage.style.display = 'none'; // Hide the popup
};

// Add event listener for the signout button
document.querySelector("#signout").addEventListener("click", logout);

// Logout function with async/await;
async function logout() {
  try {
    // Send the logout request using `fetch`
    const res = await fetch('http://localhost:3030/root/root/logout?key=87', { 
      method: 'POST',
      headers: {
        "Content-Type": "application/json"
      }
    }); // Semicolon after this line is correct

    // Check if the request was successful
    if (res.ok) {
      console.log("Logout successful...");
    } else {
      console.error("Failed to logout...");
    }
  } catch (error) {
    // Handle any errors during the logout request
    console.error("An error occurred during logout:", error);
  } finally {
    // Remove admin data from localStorage
    localStorage.removeItem("adminData");

    // Redirect to login page
    window.location.href = "./login.html";

    // Prevent going back to the previous page
    history.pushState(null, null, window.location.href);
  }
}
