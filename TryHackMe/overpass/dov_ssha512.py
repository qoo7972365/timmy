#This is a porting of https://gist.github.com/garrettreid/8329796
# to Python 3 with some more additions (time compare, dialogs etc)
# for Python 2.x use the above gist

from hashlib import sha512
import os
import base64
from operator  import _compare_digest as constant_time_compare


class SSHA512Hasher:

    def __init__(self, salt=os.urandom(16)):
        self.salt = salt

    def encode(self, password, salt=None):
        assert password is not None
        if not salt:
            salt = self.salt
        sha = sha512()
        password = password.encode('utf-8')
        sha.update(password)
        sha.update(salt)
        ssha512 = base64.b64encode(sha.digest() + salt)

        # Print it out with a prefix for Dovecot compatibility
        return "{{SSHA512}}{}".format(ssha512.decode('utf-8'))

    def verify(self, password, encoded):
        striped = encoded.replace('{SSHA512}', '')
        decoded = base64.b64decode(striped)
        #we don't care how big salt is. everything after 64 is salt
        salt = decoded[64::]
        encoded_2 = self.encode(password, salt)
        return constant_time_compare(encoded, encoded_2)

if __name__ == '__main__':
    shahasher = SSHA512Hasher()
    choice = input('Enter [a] for encode\n[b] for decode:\n')
    password = input('Enter a password:\n')
    if choice is 'a':
        print(shahasher.encode(password))
    elif choice is 'b':
        dovhash = input('Enter the hash with the {{SSHA512}} prefix:\n')
        if shahasher.verify(password, dovhash):
            print("It's a match!")
        else:
            print("Pass doesn't match the hash!")
    else:
        print('You entered an incorrect option!\nExiting......')
        exit(1)