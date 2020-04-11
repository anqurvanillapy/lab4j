from socket import socket, AF_INET, SOCK_STREAM

with socket(AF_INET, SOCK_STREAM) as s:
    s.bind(("localhost", 30000))
    s.listen(1)
    conn, addr = s.accept()
    with conn:
        print(f"{addr}: connect")
        while True:
            try:
                data = conn.recv(1024)
                if not data:
                    break
                print(f"{addr}: '{data.decode('utf-8').strip()}'")
                conn.sendall(data)
            except ConnectionAbortedError:
                break
