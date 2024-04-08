#!/bin/bash
TIME_EXECUTION_START=$(date +"%s")

# GET THE DATE CURRENT YEAR, MONTH AND DAY
DATE=$(date +"%Y-%m-%d")
CURRENT_YEAR=$(date +"%Y")
CURRENT_MONTH=$(date +"%m")
CURRENT_DAY=$(date +"%d")

PREVIOUS_DATE=$(date -d "$DATE - 1 day" +%Y-%m-%d)
PREVIOUS_YEAR=$(date -d "$DATE - 1 day" +%Y)
PREVIOUS_MONTH=$(date -d "$DATE - 1 day" +%m)
PREVIOUS_DAY=$(date -d "$DATE - 1 day" +%d)


echo "____________________________________"
echo " DATE CURRENT: $DATE"
echo "____________________________________"

echo "____________________________________"
echo " DATE PREVIOUS: $PREVIOUS_DATE"

echo "____________________________________"
echo " CHECKING BACKUPS"
echo "____________________________________"

# Check if the year is the same as the current year
if [ "$PREVIOUS_YEAR" == "$CURRENT_YEAR" ]; then
  # Check if the month is the same as the current month
  if [ "$PREVIOUS_MONTH" -lt "$CURRENT_MONTH" ]; then
    if [ -d "$DB_BACKUP_DIR/$CURRENT_YEAR/$CURRENT_MONTH/BACKUP_MYSQL_$CURRENT_DAY" ]; then
      if [ -d "$DB_BACKUP_DIR/$PREVIOUS_YEAR/$PREVIOUS_MONTH" ]; then
         echo "Deleting $DB_BACKUP_DIR/$PREVIOUS_YEAR/$PREVIOUS_MONTH"
         rm -rf $DB_BACKUP_DIR/$PREVIOUS_YEAR/$PREVIOUS_MONTH
      fi
    fi
  fi
else
#  less than 1 year
  if [ "$PREVIOUS_YEAR" -lt "$CURRENT_YEAR" ]; then
    if [ -d "$DB_BACKUP_DIR/$CURRENT_YEAR/$CURRENT_MONTH/BACKUP_MYSQL_$CURRENT_DAY" ]; then
      if [ -d "$DB_BACKUP_DIR/$PREVIOUS_YEAR" ]; then
         echo "Deleting $DB_BACKUP_DIR/$PREVIOUS_YEAR"
         rm -rf $DB_BACKUP_DIR/$PREVIOUS_YEAR
      fi
    fi
  fi
fi




