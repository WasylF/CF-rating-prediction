#!/usr/bin/env sh
ab -n 101 -c 10 $1"/GetNextRatingServlet?contestId="$2
