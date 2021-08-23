rootProject.name = "ucommand"

include("core")
findProject(":core")?.name = "ucommand-core"

include("std")
findProject(":std")?.name = "ucommand-std"
