config_githooks:
	git config core.hooksPath .githooks

up:
	docker-compose up -d

down:
	docker-compose down -v

test_ui:
	./gradlew clean uitest

test_ui_remote:
	./gradlew clean uitest -Dselenide.remote=http://user:password@localhost:4444/wd/hub

format:
	./gradlew ktlintFormat
	./gradlew detekt

report:
	./gradlew allureReport
	./gradlew allureServe
