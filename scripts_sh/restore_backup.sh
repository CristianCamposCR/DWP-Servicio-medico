#!/bin/bash
TIME_EXECUTION_START=$(date +"%s")

# Enter name of the file to decrypt
read -p "Enter year: " YEAR
read -p "Enter month: " MONTH
read -p "Enter day: " DAY

DATE="$YEAR/$MONTH"
DIRECTORY="$DB_BACKUP_DIR/$DATE/BACKUP_MYSQL_$DAY"
DIRECTORY_OUTPUT_UNCOMPRESS="$DB_BACKUP_DIR/$DATE/"

printf "____________________________________\n"
printf "Starting restore\n"
printf "____________________________________\n"


printf "____________________________________\n"
printf "Decrypting and uncompressing\n"
printf "____________________________________\n"

# check if the file exists
if [ -f "$DIRECTORY.7z.gpg" ]; then
    # Decrypt
    echo $ENCRYPTION_PASSWORD | gpg --batch --yes --passphrase-fd 0 --decrypt --cipher-algo AES256 --output "$DIRECTORY.7z" "$DIRECTORY.7z.gpg"

    # Uncompress
    7z x -p$ZIP_PASSWORD "$DIRECTORY.7z" -o"$DIRECTORY_OUTPUT_UNCOMPRESS"

    # Delete compressed file
    rm "$DIRECTORY.7z"

else
    echo "$DIRECTORY.7z.gpg does not exist."
fi

printf "____________________________________\n"
printf "Decrypted and uncompressed\n"
printf "____________________________________\n"


if [ -d "$DIRECTORY" ]; then

    printf "_________ stop mysql __________\n"
    service mysql stop


    printf "________ Move data __________\n"
    if [ -d "/var/lib/mysql_old" ]; then
        rm -rf /var/lib/mysql_old
    fi

    # Move data to old
    mv /var/lib/mysql /var/lib/mysql_old

    if [ -d "/var/lib/mysql" ]; then
        rm -rf /var/lib/mysql
    fi

    printf "_________ Prepare to restore __________\n"
    xtrabackup --prepare --target-dir=$DIRECTORY

    printf "_________ Restore __________\n"
    xtrabackup --copy-back --target-dir=$DIRECTORY --datadir=/var/lib/mysql
else
    echo "$DIRECTORY does not exist."
fi

if [ -d "/var/lib/mysql" ]; then
    printf "_________ Change permissions __________\n"
    chown -R mysql:mysql /var/lib/mysql

    printf "_________ Start mysql __________\n"
    service mysql start

    printf "____________________________________\n"
    printf "Restored\n"
    printf "____________________________________\n"
fi


if [ -d "$DIRECTORY" ]; then
    rm -rf $DIRECTORY
fi


TIME_EXECUTION_END=$(date +"%s")
TIME_EXECUTION=$(($TIME_EXECUTION_END - $TIME_EXECUTION_START))

printf "____________________________________\n"
printf "RESUMEN\n"
printf "____________________________________\n"

echo "Time of execution: $TIME_EXECUTION seconds\n"



