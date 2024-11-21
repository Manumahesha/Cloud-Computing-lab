def generate_public_key(prime, base, private_key):
    return pow(base, private_key, prime)

def generate_shared_secret(public_key, private_key, prime):
    return pow(public_key, private_key, prime)

prime = int(input("Enter a large prime number (recommended 2048 bits): "))
base = int(input("Enter a base (primitive root modulo of the prime): "))
alice_private = int(input("Alice, enter your private key (a random integer less than prime): "))
alice_public = generate_public_key(prime, base, alice_private)
print("Alice's public key:", alice_public)

bob_private = int(input("Bob, enter your private key (a random integer less than prime): "))
bob_public = generate_public_key(prime, base, bob_private)
print("Bob's public key:", bob_public)

alice_shared_secret = generate_shared_secret(bob_public, alice_private, prime)
bob_shared_secret = generate_shared_secret(alice_public, bob_private, prime)

print("Alice's shared secret:", alice_shared_secret)
print("Bob's shared secret:", bob_shared_secret)

if alice_shared_secret == bob_shared_secret:
    print("Success! The shared secret is", alice_shared_secret)
else:
    print("Error: Shared secrets do not match")

output
Enter a large prime number (recommended 2048 bits): 23
Enter a base (primitive root modulo of the prime): 5
Alice, enter your private key (a random integer less than prime): 6
Alice's public key: 8
Bob, enter your private key (a random integer less than prime): 8
Bob's public key: 16
Alice's shared secret: 4
Bob's shared secret: 4
Success! The shared secret is 4
