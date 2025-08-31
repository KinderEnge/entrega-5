import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { appConfig } from './app/app.config';
import { AppModule } from './app/app';

bootstrapApplication(AppModule, appConfig)
  .catch((err) => console.error(err));
