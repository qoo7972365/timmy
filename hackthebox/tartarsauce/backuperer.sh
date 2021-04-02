#!/bin/bash

#-------------------------------------------------------------------------------------
# backuperer ver 1.0.2 - by ȜӎŗgͷͼȜ
# ONUMA Dev auto backup program
# This tool will keep our webapp backed up incase another skiddie defaces us again.
# We will be able to quickly restore from a backup in seconds ;P
#-------------------------------------------------------------------------------------

# Set Vars Here
basedir=/var/www/html
bkpdir=/var/backups
tmpdir=/var/tmp
testmsg=/var/backups/onuma_backup_test.txt
errormsg=/var/backups/onuma_backup_error.txt
tmpfile=/var/tmp/921fa090f3f390fc6fe1bb6689e6ddb88b0287a1
check=/var/tmp/check

# formatting
printbdr()
{
    for n in $(seq 72);
    do /usr/bin/printf $"-";
    done
}
bdr=$(printbdr)

# Added a test file to let us see when the last backup was run
/usr/bin/printf $"$bdr\nAuto backup backuperer backup last ran at : $(/bin/date)\n$bdr\n" > /var/backups/onuma_backup_test.txt

# Cleanup from last time.
/bin/rm -rf /var/tmp/.* /var/tmp/check

# Backup onuma website dev files.
/usr/bin/sudo -u onuma /bin/tar -zcvf /var/tmp/921fa090f3f390fc6fe1bb6689e6ddb88b0287a1 /var/www/html &

# Added delay to wait for backup to complete if large files get added.
/bin/sleep 30

# Test the backup integrity
integrity_chk()
{
    /usr/bin/diff -r /var/www/html /var/tmp/check/var/www/html
}

/bin/mkdir /var/tmp/check
/bin/tar -zxvf /var/tmp/921fa090f3f390fc6fe1bb6689e6ddb88b0287a1 -C /var/tmp/check
if [[ $(integrity_chk) ]]
then
    # Report errors so the dev can investigate the issue.
    /usr/bin/printf $"$bdr\nIntegrity Check Error in backup last ran :  $(/bin/date)\n$bdr\n/var/tmp/921fa090f3f390fc6fe1bb6689e6ddb88b0287a1\n" >> /var/backups/onuma_backup_error.txt
    integrity_chk >> /var/backups/onuma_backup_error.txt
    exit 2
else
    # Clean up and save archive to the bkpdir.
    /bin/mv /var/tmp/921fa090f3f390fc6fe1bb6689e6ddb88b0287a1 /var/backups/onuma-www-dev.bak
    /bin/rm -rf /var/tmp/check .*
    exit 0
fi
