select distinct id, list_id, summary from TASKS where id in (
    select task_id from HINTS where (
        (hint_condition='DAILY' and DATE(last_update)<CURRENT_DATE)
        or 
        (hint_condition='ANY_WEEKEND_DAY' and (DAYOFWEEK(last_update) not in (1,7) OR WEEKOFYEAR(last_update)<WEEKOFYEAR(CURRENT_DATE) OR YEAR(last_update)<YEAR(CURRENT_DATE)) and DAYOFWEEK(CURRENT_DATE) in (1, 7))
        or 
        (hint_condition='ANY_WORKDAY' and DAYOFWEEK(CURRENT_DATE) not in (1, 7) and (DAYOFWEEK(last_update) in (1,7) OR WEEKOFYEAR(last_update)<WEEKOFYEAR(CURRENT_DATE) or YEAR(last_update)<YEAR(CURRENT_DATE)))
        or 
        (hint_condition='EVERY_WORKDAY' and DAYOFWEEK(CURRENT_DATE) not in (1, 7) and (DATE(last_update)<>CURRENT_DATE))
        or 
        (hint_condition='EVERY_WEEKEND_DAY' and DAYOFWEEK(CURRENT_DATE) in (1, 7) and (DATE(last_update)<>CURRENT_DATE))        
        or
        (hint_condition='WEEKLY' and (WEEKOFYEAR(last_update)<>WEEKOFYEAR(CURRENT_DATE) or YEAR(last_update)<>YEAR(CURRENT_DATE)))
        or 
        (hint_condition='MONTHLY' and (MONTH(last_update)<>MONTH(CURRENT_DATE) or YEAR(last_update)<>YEAR(CURRENT_DATE)))
        or 
        (hint_condition='MONTHLY' and YEAR(last_update)<>YEAR(CURRENT_DATE))
        or 
        (hint_condition='SUNDAY'  and DAYOFWEEK(CURRENT_DATE)=1 and DATE(last_update)<>DATE(CURRENT_DATE))
        or
        (hint_condition='MONDAY'  and DAYOFWEEK(CURRENT_DATE)=2 and DATE(last_update)<>DATE(CURRENT_DATE))
        or
        (hint_condition='TUESDAY'  and DAYOFWEEK(CURRENT_DATE)=3 and DATE(last_update)<>DATE(CURRENT_DATE))
        or
        (hint_condition='WEDNESDAY'  and DAYOFWEEK(CURRENT_DATE)=4 and DATE(last_update)<>DATE(CURRENT_DATE))
        or
        (hint_condition='THURSDAY'  and DAYOFWEEK(CURRENT_DATE)=5 and DATE(last_update)<>DATE(CURRENT_DATE))
        or
        (hint_condition='FRIDAY'  and DAYOFWEEK(CURRENT_DATE)=6 and DATE(last_update)<>DATE(CURRENT_DATE))
        or
        (hint_condition='SATURDAY'  and DAYOFWEEK(CURRENT_DATE)=7 and DATE(last_update)<>DATE(CURRENT_DATE))
    ) and (
        task_id in (
            select id from TASKS where 
                is_finished=false and 
                is_cancelled=false and list_id IN (
                    select id from LISTS where context_id IN (select id from CONTEXTS where user_id=?)
            )
        )
    )
)