library(ggplot2)
library(tidyr)

cat("Starting analysis...\n")

# Load results
data <- read.csv("../../simulation_results.csv")

print(data)

# -------------------------------
# Cascading failure comparison
# -------------------------------
p1 <- ggplot(data, aes(x = service, y = cascaded_failures)) +
  geom_bar(stat = "identity", fill = "steelblue") +
  theme_minimal() +
  theme(axis.text.x = element_text(angle = 45, hjust = 1)) +
  labs(
    title = "Cascading Failures by Service",
    x = "Service",
    y = "Cascaded Failures"
  )

ggsave("cascaded_failures.png", p1, width = 8, height = 5)

# -------------------------------
# Direct vs Cascaded comparison
# (FIXED: grouped bars instead of overlap)
# -------------------------------

data_long <- pivot_longer(
  data,
  cols = c(direct_failures, cascaded_failures),
  names_to = "failure_type",
  values_to = "count"
)

p2 <- ggplot(data_long, aes(x = service, y = count, fill = failure_type)) +
  geom_bar(stat = "identity", position = "dodge") +
  theme_minimal() +
  theme(axis.text.x = element_text(angle = 45, hjust = 1)) +
  labs(
    title = "Direct vs Cascaded Failures",
    x = "Service",
    y = "Failure Count",
    fill = "Failure Type"
  )

ggsave("failure_comparison.png", p2, width = 8, height = 5)

# -------------------------------
# Observed failure rate
# -------------------------------
p3 <- ggplot(data, aes(x = service, y = lambda_observed)) +
  geom_bar(stat = "identity", fill = "darkblue") +
  theme_minimal() +
  theme(axis.text.x = element_text(angle = 45, hjust = 1)) +
  labs(
    title = "Observed Failure Rate",
    x = "Service",
    y = "Lambda Observed"
  )

ggsave("lambda_observed.png", p3, width = 8, height = 5)

cat("Graphs generated successfully.\n")