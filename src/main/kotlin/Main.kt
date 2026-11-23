import cli.ArgumentParser
import cli.Mode

fun main(args: Array<String>) {
    try {
        val config = ArgumentParser.parser(args)

        val runner = Runner(config)

        when (config.mode) {
            Mode.KEYGEN, Mode.SIGN -> runner.run()
            Mode.VERIFY -> {
                val isValid = checkNotNull(runner.run())
                println(if (isValid) "Подпись действительна" else "Подпись недействительна")
            }
        }
    } catch (e: IllegalArgumentException) {
        System.err.println(e.message)
    }
}
