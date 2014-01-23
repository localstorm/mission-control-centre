select elementary.cid cid, cname, effort1, effort2, effort3, effort4, effort5, hinted
from (
    
        (select id cid, name cname, IF(total IS NULL, 0, total) effort1  from (
                    (select name, id from CONTEXTS where user_id=? and is_archived=false) c
                    LEFT OUTER JOIN
                    (
                        select context_id, SUM(lcnt) total from
                        (
                            select context_id, lcnt from
                            (
                                LISTS lst
                                JOIN (
                                    select list_id, SUM(1) lcnt from TASKS where
                                        is_cancelled=false and
                                        is_finished=false and
                                        is_awaited=false and
                                        is_delegated=false and
                                        effort=1 and
                                        id not in (select task_id from HINTS)
                                    GROUP BY list_id
                                ) lc ON lst.id=lc.list_id
                            )
                        ) ctx GROUP BY ctx.context_id
                    ) co
                    ON c.id=co.context_id
            )
        ) elementary
        JOIN
        (
            select id cid, IF(total IS NULL, 0, total) effort2  from (
                    (select name, id from CONTEXTS where user_id=? and is_archived=false) c
                    LEFT OUTER JOIN
                    (
                        select context_id, SUM(lcnt) total from
                        (
                            select context_id, lcnt from
                            (
                                LISTS lst
                                JOIN (
                                    select list_id, SUM(1) lcnt from TASKS where
                                        is_cancelled=false and
                                        is_finished=false and
                                        is_awaited=false and
                                        is_delegated=false and
                                        effort=2 and
                                        id not in (select task_id from HINTS)
                                    GROUP BY list_id
                                ) lc ON lst.id=lc.list_id
                            )
                        ) ctx GROUP BY ctx.context_id
                    ) co
                    ON c.id=co.context_id
            )
        ) easy
        ON elementary.cid=easy.cid
        JOIN
        (
            select id cid, IF(total IS NULL, 0, total) effort3  from (
                    (select name, id from CONTEXTS where user_id=? and is_archived=false) c
                    LEFT OUTER JOIN
                    (
                        select context_id, SUM(lcnt) total from
                        (
                            select context_id, lcnt from
                            (
                                LISTS lst
                                JOIN (
                                    select list_id, SUM(1) lcnt from TASKS where
                                        is_cancelled=false and
                                        is_finished=false and
                                        is_awaited=false and
                                        is_delegated=false and
                                        effort=3 and
                                        id not in (select task_id from HINTS)
                                    GROUP BY list_id
                                ) lc ON lst.id=lc.list_id
                            )
                        ) ctx GROUP BY ctx.context_id
                    ) co
                    ON c.id=co.context_id
            )
        ) medium
        ON easy.cid=medium.cid
        JOIN
        (
            select id cid, IF(total IS NULL, 0, total) effort4  from (
                    (select name, id from CONTEXTS where user_id=? and is_archived=false) c
                    LEFT OUTER JOIN
                    (
                        select context_id, SUM(lcnt) total from
                        (
                            select context_id, lcnt from
                            (
                                LISTS lst
                                JOIN (
                                    select list_id, SUM(1) lcnt from TASKS where
                                        is_cancelled=false and
                                        is_finished=false and
                                        is_awaited=false and
                                        is_delegated=false and
                                        effort=4 and
                                        id not in (select task_id from HINTS)
                                    GROUP BY list_id
                                ) lc ON lst.id=lc.list_id
                            )
                        ) ctx GROUP BY ctx.context_id
                    ) co
                    ON c.id=co.context_id
            )
        ) difficult
        ON medium.cid=difficult.cid
        JOIN
        (
            select id cid, IF(total IS NULL, 0, total) effort5  from (
                    (select name, id from CONTEXTS where user_id=? and is_archived=false) c
                    LEFT OUTER JOIN
                    (
                        select context_id, SUM(lcnt) total from
                        (
                            select context_id, lcnt from
                            (
                                LISTS lst
                                JOIN (
                                    select list_id, SUM(1) lcnt from TASKS where
                                        is_cancelled=false and
                                        is_finished=false and
                                        is_awaited=false and
                                        is_delegated=false and
                                        effort=5 and
                                        id not in (select task_id from HINTS)
                                    GROUP BY list_id
                                ) lc ON lst.id=lc.list_id
                            )
                        ) ctx GROUP BY ctx.context_id
                    ) co
                    ON c.id=co.context_id
            )
        ) vd
        ON difficult.cid=vd.cid
        JOIN
        (
            select id cid, IF(total IS NULL, 0, total) hinted  from (
                    (select name, id from CONTEXTS where user_id=? and is_archived=false) c
                    LEFT OUTER JOIN
                    (
                        select context_id, SUM(lcnt) total from
                        (
                            select context_id, lcnt from
                            (
                                LISTS lst
                                JOIN (
                                    select list_id, SUM(1) lcnt from TASKS where
                                        is_cancelled=false and
                                        is_finished=false and
                                        is_awaited=false and
                                        is_delegated=false and
                                        id in (select task_id from HINTS)
                                    GROUP BY list_id
                                ) lc ON lst.id=lc.list_id
                            )
                        ) ctx GROUP BY ctx.context_id
                    ) co
                    ON c.id=co.context_id
            )
        ) hnt
        ON vd.cid=hnt.cid
) ORDER BY cname