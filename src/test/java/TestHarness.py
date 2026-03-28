import subprocess
import random
import string

JAVA_CMD = [
    r"C:\Program Files\Apache\Maven\bin\mvn.cmd",
    "compile",
    "exec:java",
    "-Dexec.mainClass=com.exppoints.fantasy.MainTest",
    "-Dexec.args=PLACEHOLDER"
]

def run_test(name):
    cmd = [part if part != "PLACEHOLDER" else name for part in JAVA_CMD]

    result = subprocess.run(
        cmd,
        capture_output=True,
        text=True
    )
    return result.stdout.strip()

players = ["josh-allen-qb", "bryce-young", "patrick-mahomes", "jim-pine", "yusbdfjhs"]

for p in players:
    output = run_test(p)
    print(f"Test for {p}: {output}")

#fuzz
def random_name():
    return ''.join(random.choices(string.ascii_lowercase + string.digits, k=10))

for _ in range(20):
    name = random_name()
    output = run_test(name)

    if "Exception" in output:
        print(f"Fuzz test with name '{name}' caused an exception: {output}")