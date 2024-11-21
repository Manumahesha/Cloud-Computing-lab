from cryptography.hazmat.primitives.asymmetric import ec
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives import serialization

private_key = ec.generate_private_key(ec.SECP256R1())
public_key = private_key.public_key()

public_pem = public_key.public_bytes(
    encoding=serialization.Encoding.PEM,
    format=serialization.PublicFormat.SubjectPublicKeyInfo
)

print("Public key in PEM format:\n", public_pem.decode())

message = input("Enter a message to be signed: ").encode()

signature = private_key.sign(
    message,
    ec.ECDSA(hashes.SHA256())
)

print("Signature:", signature)

try:
    public_key.verify(signature, message, ec.ECDSA(hashes.SHA256()))
    print("Signature is valid")
except:
    print("Signature verification failed.")

output
Public key in PEM format:
 -----BEGIN PUBLIC KEY-----
MFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEy+/Lu6VVA0YwYSeCXGvdKBixhMoF
Tp09jT7mRwS/vGppKJyMWTgQFcMFcB7qAJ1YGJS7OBE9PM/wsVwJix7npg==
-----END PUBLIC KEY-----

Enter a message to be signed: Hello
Signature: b'0E\x02!\x00\x83#h\xd9D\xcc\x18\x8bX\xcf\xd7\x1a\x00H\r^\x0eh\xd9[\xa7{\xec\r*\xec\x7f\xd5\xab\x88\xe3\xcc\x02 3!a\xf6To\xb5\x99J5\xaa\x92\xb4\x9d\x08\x1e\xc4B3H\xa4\x16}\xa0\xe3\xbb\x84#\x8a\xa6X\x9b'
Signature is valid
