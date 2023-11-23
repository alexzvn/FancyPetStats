import java.net.URI

sourceControl {
    gitRepository(URI("https://github.com/Nocsy-Workshop/mcpets.git")) {
        producesModule("fr.nocsy.mcpets:pets")
    }
}

rootProject.name = "FancyPetStats"