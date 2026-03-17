# dependency_network.R

library(igraph)

cat("Loading dependency data...\n")

# Load dependencies CSV
deps <- read.csv("../src/main/resources/dependencies.csv")

print(deps)

# Create directed graph
g <- graph_from_data_frame(deps, directed = TRUE)

# Highlight important services
V(g)$color <- ifelse(
  V(g)$name == "api_gateway", "#FF6B6B",
  ifelse(
    V(g)$name %in% c("data_storage","cache_service"), "#74C476",
    ifelse(
      V(g)$name %in% c("message_queue","payment_gateway"), "#FDAE6B",
      "#6BAED6"
    )
  )
)

# Node sizes
V(g)$size <- ifelse(V(g)$name == "api_gateway", 50, 40)

# Create hierarchical layout (best for dependency graphs)
layout <- layout_with_sugiyama(g)$layout

# Save high-resolution image
png("dependency_graph_layered.png", width=1400, height=1000)

plot(
  g,
  layout = layout,
  
  # Vertex styling
  vertex.frame.color = "black",
  vertex.label.color = "black",
  vertex.label.cex = 1.1,
  vertex.label.family = "sans",
  
  # Edge styling
  edge.arrow.size = 0.4,
  edge.width = 1.8,
  edge.color = "gray40",
  edge.curved = 0.1,
  
  # Spacing
  margin = 0.2,
  
  # Title
  main = "Layered Microservice Dependency Architecture"
)

dev.off()

cat("Dependency graph saved as dependency_graph_layered.png\n")