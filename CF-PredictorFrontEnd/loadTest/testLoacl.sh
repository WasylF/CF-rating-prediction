#!/usr/bin/env sh
	sh abTest.sh http://localhost:8084/CF-PredictorFrontEnd/ 860 &
	sh abTest.sh http://localhost:8084/CF-PredictorFrontEnd/ 823 &
	sh abTest.sh http://localhost:8084/CF-PredictorFrontEnd/ 756 &
	wait
	sleep 0.3

	sh abTest.sh http://localhost:8084/CF-PredictorFrontEnd/ 860 &
	sh abTest.sh http://localhost:8084/CF-PredictorFrontEnd/ 823 &
	sh abTest.sh http://localhost:8084/CF-PredictorFrontEnd/ 756 &
	wait
	sleep 0.3

	sh abTest.sh http://localhost:8084/CF-PredictorFrontEnd/ 860 &
	sh abTest.sh http://localhost:8084/CF-PredictorFrontEnd/ 823 &
	sh abTest.sh http://localhost:8084/CF-PredictorFrontEnd/ 756 &
	wait
	sleep 0.3
