# Dummy AEM Project — GitHub Secrets + OSGI Config Validation

## Purpose
This project validates that **GitHub Secrets** can be used to inject sensitive values (API keys) into AEM OSGI configuration files during a CI/CD build.

## How It Works

1. **Placeholder in OSGI config**: The file  
   `ui.config/src/main/content/jcr_root/apps/myproject/osgiconfig/config/com.myproject.core.services.impl.ApiServiceImpl.cfg.json`  
   contains `__API_KEY__` as a placeholder.

2. **GitHub Secret**: You store the real API key as a repository secret named `API_KEY`.

3. **GitHub Actions workflow**: During the build, the workflow:
   - Replaces `__API_KEY__` with the secret value using `sed`
   - Builds the Maven project
   - Verifies the built content package no longer contains the placeholder

## Setup Steps

### 1. Create a GitHub Repository
```bash
git init
git add .
git commit -m "Initial commit - OSGI secrets test"
git remote add origin https://github.com/<your-username>/aem-secrets-test.git
git push -u origin main
```

### 2. Add the Secret
1. Go to your repo on GitHub → **Settings** → **Secrets and variables** → **Actions**
2. Click **New repository secret**
3. Name: `API_KEY`
4. Value: any test value (e.g., `my-super-secret-api-key-12345`)

### 3. Trigger the Workflow
- Push to `main` or manually trigger via **Actions** → **Run workflow**

### 4. Check Results
- Go to **Actions** tab → click the workflow run
- In the **"Inject API Key into OSGI config"** step, verify the placeholder was replaced
- In the **"Validate content package"** step, verify the packaged ZIP has the secret injected
- Download the artifact to inspect the final content package

## What This Proves

| Validation | Status |
|-----------|--------|
| GitHub Secret is accessible in workflow | ✅ Verified by sed replacement |
| Placeholder is replaced before build | ✅ Verified by grep check |
| Built content package contains real value | ✅ Verified by unzip + grep |
| Secret is NOT printed in logs | ✅ GitHub auto-masks secret values |

## Project Structure
```
├── .github/workflows/build.yml    ← CI workflow with secret injection
├── core/                          ← Minimal Java module
├── ui.config/                     ← OSGI config content package
│   └── src/main/content/jcr_root/apps/myproject/osgiconfig/config/
│       └── com.myproject.core.services.impl.ApiServiceImpl.cfg.json
└── pom.xml                        ← Reactor POM
```
