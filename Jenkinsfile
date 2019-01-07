pipeline {
    agent any

    stages {
        stage('Test') {
            steps {
                /* `make check` returns non-zero on test failures,
                * using `true` to allow the Pipeline to continue nonetheless
                */
                sh 'make check || true'
                junit 'C:\Users\Ricardo\Documents\GitHub\ESIIProject\build\test-results\test\TEST-Teste.xml'
            }
        }
    }
}
