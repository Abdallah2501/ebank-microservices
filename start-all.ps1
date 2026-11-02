$services = @("discovery-service", "config-service", "customer-service", "ebank-service", "gateway-service")

foreach ($s in $services) {
    Start-Process powershell -ArgumentList "-NoExit", "-Command", "cd '$PSScriptRoot\$s'; .\mvnw spring-boot:run"
    if ($s -eq "discovery-service") { Start-Sleep -Seconds 20 }
    elseif ($s -eq "config-service") { Start-Sleep -Seconds 15 }
    else { Start-Sleep -Seconds 3 }
}