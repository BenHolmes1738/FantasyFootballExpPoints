import subprocess
import random
import string

def run_test(name):
    cmd = [
        r"C:\Users\bende\276Assignments\apache-maven-3.9.11-bin\apache-maven-3.9.11\bin\mvn.cmd",
        "compile",
        "exec:java",
        "-Dexec.mainClass=com.exppoints.fantasy.MainTest",
        f"-Dexec.args={name}"
    ]

    result = subprocess.run(
        cmd,
        capture_output=True,
        text=True
    )
    return result.stdout.strip()

players = ["josh-allen-qb", "bryce-young", "patrick-mahomes", "jim-pine", "yusbdfjhs"]

for p in players:
    print(f"Running test for player: {p}")
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