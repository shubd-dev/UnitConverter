// Tab functionality
function showTab(tab) {
  document.querySelectorAll('.converter-section').forEach(sec => sec.classList.remove('active'));
  document.querySelectorAll('.tab').forEach(tabBtn => tabBtn.classList.remove('active'));
  document.getElementById(tab + '-section').classList.add('active');
  if (tab === 'length') document.querySelectorAll('.tab')[0].classList.add('active');
  if (tab === 'weight') document.querySelectorAll('.tab')[1].classList.add('active');
  if (tab === 'temperature') document.querySelectorAll('.tab')[2].classList.add('active');
}

// Attach event listeners after DOM is loaded
window.onload = function() {
  document.getElementById('length-convert-btn').addEventListener('click', convertLength);
  document.getElementById('weight-convert-btn').addEventListener('click', convertWeight);
  document.getElementById('temp-convert-btn').addEventListener('click', convertTemperature);
};

// Generic function to call the backend
async function fetchConversion(type, value, from, to, resultId) {
  if (isNaN(value) || value === '') {
    document.getElementById(resultId).textContent = "Please enter a value.";
    return;
  }
  try {
    const response = await fetch('http://localhost:8080/api/convert', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        type: type,
        value: value,
        from: from,
        to: to
      })
    });
    if (!response.ok) {
      throw alert("Conversion Failed")
    }
    const data = await response.json();
    document.getElementById(resultId).textContent =
      `${value} ${from} = ${data.result} ${to}`;
  } catch (error) {
    document.getElementById(resultId).textContent = "Error: " + error.message;
  }
}

function convertLength() {
  const value = parseFloat(document.getElementById('length-value').value);
  const from = document.getElementById('length-from').value;
  const to = document.getElementById('length-to').value;
  fetchConversion('length', value, from, to, 'length-result');
}

function convertWeight() {
  const value = parseFloat(document.getElementById('weight-value').value);
  const from = document.getElementById('weight-from').value;
  const to = document.getElementById('weight-to').value;
  fetchConversion('weight', value, from, to, 'weight-result');
}

function convertTemperature() {
  const value = parseFloat(document.getElementById('temp-value').value);
  const from = document.getElementById('temp-from').value;
  const to = document.getElementById('temp-to').value;
  fetchConversion('temperature', value, from, to, 'temp-result');
}
