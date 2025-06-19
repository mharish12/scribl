// npm install axios sockjs-client stompjs
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import SockJS from 'sockjs-client';
import { over } from 'stompjs';

function RealtimeEditor({ docId = 'doc1' }) {
  const [content, setContent] = useState('');
  const [stompClient, setStompClient] = useState(null);

  // Load initial content
  useEffect(() => {
    axios
      .get(`/api/documents/${docId}`)
      .then((res) => {
        setContent(res.data.content || '');
      })
      .catch(() => console.log('new doc'));
  }, [docId]);

  // Connect WebSocket once at mount
  useEffect(() => {
    const socket = new SockJS('/ws');
    const client = over(socket);
    client.connect({}, () => {
      client.subscribe('/topic/doc', (msg) => {
        const payload = JSON.parse(msg.body);
        if (payload.documentId === docId) {
          setContent(payload.content);
        }
      });
    });
    setStompClient(client);
    return () => client.disconnect();
  }, [docId]);

  const handleChange = (e) => {
    const newText = e.target.value;
    setContent(newText);
    if (stompClient?.connected) {
      stompClient.send(
        '/app/edit',
        {},
        JSON.stringify({ documentId: docId, content: newText })
      );
    }
  };

  return (
    <textarea value={content} onChange={handleChange} rows={20} cols={60} />
  );
}

export default RealtimeEditor;
