import java.util.Scanner;
abstract class ReportGenerator {
    public final void generateReport() {
        gatherData();
        formatData();
        generateTitle();
        saveReport();
        if (customerWantsEmail()) {
            sendEmail();
        }
    }

    protected abstract void gatherData();
    protected abstract void formatData();
    protected abstract void generateTitle();
    protected abstract void saveReport();

    protected boolean customerWantsEmail() {
        return true;
    }

    protected void sendEmail() {
        System.out.println("Отправка отчета по электронной почте...");
    }
}

class PdfReport extends ReportGenerator {
    @Override
    protected void gatherData() {
        System.out.println("Сбор данных для PDF-отчета...");
    }

    @Override
    protected void formatData() {
        System.out.println("Форматирование данных для PDF-отчета...");
    }

    @Override
    protected void generateTitle() {
        System.out.println("Генерация заголовка для PDF-отчета...");
    }

    @Override
    protected void saveReport() {
        System.out.println("Сохранение PDF-отчета...");
    }
}

class ExcelReport extends ReportGenerator {
    @Override
    protected void gatherData() {
        System.out.println("Сбор данных для Excel-отчета...");
    }

    @Override
    protected void formatData() {
        System.out.println("Форматирование данных для Excel-отчета...");
    }

    @Override
    protected void generateTitle() {
        System.out.println("Генерация заголовка для Excel-отчета...");
    }

    @Override
    protected void saveReport() {
        System.out.println("Сохранение Excel-отчета...");
    }
}

class HtmlReport extends ReportGenerator {
    @Override
    protected void gatherData() {
        System.out.println("Сбор данных для HTML-отчета...");
    }

    @Override
    protected void formatData() {
        System.out.println("Форматирование данных для HTML-отчета...");
    }

    @Override
    protected void generateTitle() {
        System.out.println("Генерация заголовка для HTML-отчета...");
    }

    @Override
    protected void saveReport() {
        System.out.println("Сохранение HTML-отчета...");
    }

    @Override
    protected boolean customerWantsEmail() {
        return false;
    }
}

public class Main_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Выберите тип отчета для генерации:");
        System.out.println("1 - PDF");
        System.out.println("2 - Excel");
        System.out.println("3 - HTML");
        System.out.print("Ваш выбор: ");

        int choice = scanner.nextInt();
        ReportGenerator report = null;

        switch (choice) {
            case 1:
                report = new PdfReport();
                break;
            case 2:
                report = new ExcelReport();
                break;
            case 3:
                report = new HtmlReport();
                break;
            default:
                System.out.println("Некорректный выбор. Попробуйте снова.");
                return;
        }

        report.generateReport();
        scanner.close();
    }
}

