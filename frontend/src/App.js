import React from 'react';
import { BrowserRouter as Router, Routes, Route, Link } from 'react-router-dom';
import RealtimeEditor from './RealtimeEditor';
import './App.css';

function Home() {
  return <h2>Welcome to Scribl!</h2>;
}

function App() {
  return (
    <Router>
      <div style={{ display: 'flex', height: '100vh' }}>
        <nav style={{ width: 200, background: '#f0f0f0', padding: 20 }}>
          <ul style={{ listStyle: 'none', padding: 0 }}>
            <li>
              <Link to="/">Home</Link>
            </li>
            <li>
              <Link to="/editor">Realtime Editor</Link>
            </li>
          </ul>
        </nav>
        <main style={{ flex: 1, padding: 20 }}>
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/editor" element={<RealtimeEditor />} />
          </Routes>
        </main>
      </div>
    </Router>
  );
}

export default App;
