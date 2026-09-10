@echo off
setlocal

REM Change directory to script location
cd /d "%~dp0"

echo =================================================================
echo                     Starting Kabootar
echo =================================================================

for %%S in (admin alert common configuration gateway monitor simulator) do (
    echo -----------------------------------------------------------------
    echo  Building %%S...
    echo -----------------------------------------------------------------
    
    cd /d "%%S"
    
    call mvn clean package -DskipTests
    
    if errorlevel 1 (
        echo.
        echo ERROR: Failed to build %%S
        exit /b 1
    )
    
    cd /d "%~dp0"
)

echo =================================================================
echo            Starting Docker Compose Containers
echo =================================================================

REM Shut down any previous runs
docker compose down --remove-orphans

REM Build containers and run in background
docker compose up --build -d

echo =================================================================
echo            Containers Successfully Launched!
echo =================================================================
echo You can check logs with:   docker compose logs -f
echo Check running containers:  docker compose ps
echo =================================================================

endlocal