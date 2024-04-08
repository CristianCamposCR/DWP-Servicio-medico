#!/bin/bash
TIME_EXECUTION_START=$(date +"%s")


YEAR=$(date +"%Y")
MONTH=$(date +"%m")
DAY=$(date +"%d")

DATE="$YEAR/$MONTH"
NAME_FOLDER_DB_BACKUP="BACKUP_MYSQL_$DAY"

DIRECTORY="$DB_BACKUP_DIR/$DATE/$NAME_FOLDER_DB_BACKUP"

#Check the mysql service
if [ -z "$(pgrep mysql)" ]; then
    printf "Mysql is not running\n"
    exit 1
fi

printf "____________________________________\n"
printf "Starting backup\n"
printf "____________________________________\n"

# Create directory if not exists
if [ ! -d "$DIRECTORY" ]; then
    printf "___________ Creating directory _____________\n"
    mkdir -p $DIRECTORY

    printf "_____________ Directory created _____________\n"
else
    printf "_____________ Directory exists _____________\n"
    exit 1
fi

# Give permissions
printf "_____________ Giving permissions _____________\n"
chmod 777 $DIRECTORY

# Backup
printf "___________ Starting xtrabackup _____________\n"
xtrabackup --backup --target-dir=$DIRECTORY --user=$DB_USER --password=$DB_PASSWORD --host=$DB_HOST --port=$DB_PORT
printf "___________ xtrabackup finished _____________\n"

printf "_______________ Verifying backup _____________\n"
xtrabackup --verify --target-dir=$DIRECTORY
printf "_______________ Backup verified _____________\n"

printf "___________ Flushing logs _____________\n"
xtrabackup --flush-logs --target-dir=$DIRECTORY
printf "___________ Logs flushed _____________\n"

printf "____________ Xtrabackup finished _____________\n"

printf "____________________________________\n"
printf "Compressing\n"
printf "____________________________________\n"

7z a -p$ZIP_PASSWORD $DIRECTORY.7z $DIRECTORY
# Check
7z t -p$ZIP_PASSWORD $DIRECTORY.7z

# Delete directory
rm -rf $DIRECTORY

printf "____________________________________\n"
printf "Compressed\n"
printf "____________________________________\n"


printf "____________________________________\n"
printf "Encryption\n"
printf "____________________________________\n"
# Encrypt
#gpg --symmetric --cipher-algo AES256 --passphrase $ENCRYPTION_PASSWORD $DIRECTORY.7z
printf "%s" "$ENCRYPTION_PASSWORD" | gpg --batch --yes --passphrase-fd 0 --symmetric --cipher-algo AES256 -o $DIRECTORY.7z.gpg $DIRECTORY.7z

#check
ls -l $DIRECTORY.7z.gpg


# Delete compressed file
rm -rf $DIRECTORY.7z

printf "____________________________________\n"
printf "Encrypted\n"
printf "____________________________________\n"


printf "____________________________________\n"
printf "Uploading to google drive\n"
printf "____________________________________\n"

curl -X POST -L \
-H "Authorization: Bearer $TOKEN_DRIVE" \
-F "metadata={name : '$NAME_FOLDER_DB_BACKUP.7z.gpg'};type=application/json;charset=UTF-8" \
-F "file=@$DIRECTORY.7z.gpg;type=application/octet-stream" \
"https://www.googleapis.com/upload/drive/v3/files?uploadType=multipart"

printf "____________________________________\n"
printf "Uploaded\n"
printf "____________________________________\n"

printf "____________________________________\n"
printf "Backup finished\n"
printf "____________________________________\n"

TIME_EXECUTION_END=$(date +"%s")
TIME_EXECUTION=$(($TIME_EXECUTION_END - $TIME_EXECUTION_START))


printf "____________________________________\n"
printf "RESUMEN\n"
printf "____________________________________\n"

echo "The backup is in $DIRECTORY.7z.gpg made on $DATE\n"
echo "Time of execution: $TIME_EXECUTION seconds\n"
