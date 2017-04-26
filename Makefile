all:
	bash run.sh
run:
	java -cp ./lib/junit-4.12.jar:./lib/hamcrest-core-1.3.jar:. org.junit.runner.JUnitCore ija.ija2016.homework2.HomeWork2Test
