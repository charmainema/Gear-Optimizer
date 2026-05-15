import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from './assets/vite.svg'
import heroImg from './assets/hero.png'
import './App.css'

function App() {
    const [input, setInput] = useState("");
    const [result, setResult] = useState("");

    const handleSubmit = async () => {
        try {
            const response = await fetch("http://localhost:8080/api/optimize", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ input: input }),
            });

            const data = await response.text(); // or .json()
            setResult(data);
        } catch (err) {
            console.error(err);
            setResult("Error connecting to API");
        }
    };

    return (
        <div style={{ padding: "2rem", fontFamily: "Arial" }}>
            <h1>Gear Optimizer</h1>
            <input
                type="text"
                placeholder="Enter input..."
                value={input}
                onChange={(e) => setInput(e.target.value)}
                style={{ padding: "0.5rem", width: "300px" }}
            />

            <br /><br />

            <button class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded" onClick={handleSubmit}>
                Optimize
            </button>

            <h2>Result:</h2>
            <p>{result}</p>
        </div>
    );
}

export default App;
