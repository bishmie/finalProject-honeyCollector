package lk.ijse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor

public class Task {
    private String taskId;
    private String taskName;
    private String Description;
    private String dueDate;
    private String beekeeperId;

}
